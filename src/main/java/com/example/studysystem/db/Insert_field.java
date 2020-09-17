package com.example.studysystem.db;

import com.example.studysystem.dao.PaperDao;
import com.example.studysystem.entity.Paper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

@Mapper
@Component
public class Insert_field {

    @Autowired
    private PaperDao paperDao;

    public void set(PaperDao paperDao) {
        this.paperDao = paperDao;
    }

    public void excute() {
        List<Paper> paperList = paperDao.getPapers();
        Map<String, List<Integer>> map = new TreeMap<>();
        List<List<Paper>> paperList2=new ArrayList<>();
        int l=paperList.size();
        for(int i=0;i<l;i+=l/20){
            List<Paper> temp=new ArrayList<>();
            for(int j=i;j<Math.min(i+l/20,l);j++){
                temp.add(paperList.get(j));
            }
            paperList2.add(temp);
        }
        for(int i=0;i<20;i++){
            Long start = System.currentTimeMillis();
            Map<String, String> paperField = resolveField(paperList2.get(i), map, 10);
            insertFields(paperField);
            Long end = System.currentTimeMillis();
            System.out.print("field part "+i+" 插入完成  ");
            System.out.println(end - start + "  ms");
        }

        //paperField = ignoreRepeat(paperField);

    }

    public boolean boringWord(String s) {
        String[] list = {"", "And", "Of", "It"};
        for (String l : list) {
            if (l.equals(s)) {
                return true;
            }
        }
        return false;
    }

    public Map<String, String> ignoreRepeat(Map<String, String> paperField) {
        Map<String, String> mapAfterDelete = new TreeMap<>();
        try {
            Connection con = MySQLconnection.getConnection();
            if (!con.isClosed()) {
                Statement statement = con.createStatement();
                String selectSQL = "SELECT id FROM field WHERE Field_name = \"%s\"";
                for (Map.Entry<String, String> a : paperField.entrySet()) {
                    String sql = String.format(selectSQL, a.getKey());
                    ResultSet rs = statement.executeQuery(sql);
                    if (rs.next()) {
                        System.out.println("repeat!");
                    } else {
                        mapAfterDelete.put(a.getKey(), a.getValue());
                    }
                }
                MySQLconnection.close(statement);
            }
            MySQLconnection.close(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mapAfterDelete;
    }

    public void insertFields(Map<String, String> paperField) {
        try {
            Connection con = MySQLconnection.getConnection();
            con.setAutoCommit(false);
            if (!con.isClosed()) {
                Statement statement = con.createStatement();
                String selectSql = "SELECT Article_Citation_Count FROM paper where id =\"%d\"";
                String selectSql2 = "select id,Paper_num,Citation_sum from field";
                String selectsql3 = "SELECT id FROM field WHERE Field_name = \"%s\"";
                PreparedStatement p1 = con.prepareStatement("insert into field(Field_name,Paper_list,Paper_num,Citation_sum)values(?,?,?,?)");
                PreparedStatement p2 = con.prepareStatement("update field set Point=? where id=?");
                PreparedStatement p3 = con.prepareStatement("update field set Paper_list=?,Paper_num=?,Citation_sum=? where id=?");
                for (Map.Entry<String, String> a : paperField.entrySet()) {//System.out.println(a.getKey()+" "+a.getValue());
                    String fieldName = a.getKey();
                    String paperList = a.getValue();
                    int paperNum = a.getValue().length() - a.getValue().replaceAll(";", "").length();
                    List<Integer> num = new ArrayList<>();
                    String[] temp = a.getValue().split(";");
                    for (String s : temp) if (!s.isEmpty()) num.add(Integer.parseInt(s));
                    int citation = 0;
                    for (int d : num) {
                        String sql = String.format(selectSql, d);
                        ResultSet rs = statement.executeQuery(sql);
                        while (rs.next()) {
                            citation += rs.getInt(1);
                        }
                    }

                    String sql3 = String.format(selectsql3, a.getKey());
                    ResultSet rs = statement.executeQuery(sql3);
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        p3.setString(1, paperList);
                        p3.setInt(2, paperNum);
                        p3.setInt(3, citation);
                        p3.setInt(4, id);
                        p3.addBatch();
                    } else {
                        p1.setString(1, fieldName);
                        p1.setString(2, paperList);
                        p1.setInt(3, paperNum);
                        p1.setInt(4, citation);
                        p1.addBatch();
                    }
                    MySQLconnection.close(rs);

                }
                p1.executeBatch();
                p3.executeBatch();
                MySQLconnection.close(p1);
                MySQLconnection.close(p3);

                String sql2 = String.format(selectSql2);
                ResultSet rs = statement.executeQuery(sql2);
                while (rs.next()) {
                    int id = rs.getInt(1);
                    int num = rs.getInt(2);
                    int citation = rs.getInt(3);
                    float p = (float) (0.7 * num + 0.3 * citation);
                    p2.setInt(2, id);
                    p2.setDouble(1, p);
                    p2.addBatch();
                }
                p2.executeBatch();
                MySQLconnection.close(p2);

                MySQLconnection.close(statement);
            }
            con.commit();
            con.setAutoCommit(true);
            MySQLconnection.close(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <K, V extends Comparable<? super V>> Map<K, V> sortByValueDescending(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                int compare = (o1.getValue()).compareTo(o2.getValue());
                return -compare;
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public Map<String, String> resolveField(List<Paper> papers, Map<String, List<Integer>> paperField, int cnt) {
        List<String> fields = new ArrayList<>();
        Map<String, Integer> map = new TreeMap<>();

        for (Paper p : papers) {
            for (String s : p.getAuthor_Keywords().split(";")) {
                if (s.isEmpty() || s.length() > 60) continue;
                s = s.substring(0, 1).toUpperCase().concat(s.substring(1).toLowerCase());
                if (boringWord(s)) continue;
                if (map.containsKey(s)) map.put(s, map.get(s) + 1);
                else map.put(s, 1);
            }
        }

        map = sortByValueDescending(map);

        for (Map.Entry<String, Integer> a : map.entrySet()) {
            if(a.getValue()>cnt) {
                fields.add(a.getKey());
                paperField.put(a.getKey(), new ArrayList<>());
            }
        }


        for (Paper p : papers) {
            int find = 0;
            for (String s : p.getAuthor_Keywords().split(";")) {
                if (s.isEmpty()) continue;
                s = s.substring(0, 1).toUpperCase().concat(s.substring(1).toLowerCase());
                for (Map.Entry<String, List<Integer>> a : paperField.entrySet()) {
                    if (a.getKey().equals(s)) {
                        find++;
                        List<Integer> list = a.getValue();
                        list.add(p.getId());
                        paperField.put(a.getKey(), list);
                    }
                }
            }
        }

        Map<String, String> ans = new HashMap<>();
        for (Map.Entry<String, List<Integer>> a : paperField.entrySet()) {
            List<Integer> newList = a.getValue().stream().distinct().collect(Collectors.toList());
            String value = "";
            for (int d : newList) value += Integer.toString(d) + ";";
            ans.put(a.getKey(), value);
        }
        return ans;
    }
}
