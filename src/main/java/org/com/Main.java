package org.com;

import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("== 명언 앱 ==");
        LinkedHashMap<Integer, String[]> map = new LinkedHashMap<>();
        Scanner sc = new Scanner(System.in);
        int i = 1;

        while(true) {
            System.out.print("명령) ");
            String cmd = sc.nextLine();

            if (cmd.equals("종료")) {
                sc.close();
                break;
            }

            if (cmd.equals("등록")) {
                System.out.print("명언 : ");
                String content = sc.nextLine();
                System.out.print("작가 : ");
                String author = sc.nextLine();
                map.put(i, new String[] {content, author});
                System.out.println(i + "번 명언이 등록되었습니다.");
                i++;
            }

            else if (cmd.equals("목록")) {
                System.out.println("번호 / 작가 / 명연");
                System.out.println("-----------------");
                Set<Integer> keySet = map.keySet();
                for(Integer key : keySet) {
                    String[] str = map.get(key);
                    System.out.println( key + " / " + str[1] + " / " + str[0]);
                }
            }

            else if (cmd.startsWith("삭제?id=")) {
                int id = Integer.parseInt(cmd.substring(6));
                if(map.get(id) != null) {
                    map.remove(id);
                    System.out.println(id + "번 명언이 삭제되었습니다.");
                }
                else {
                    System.out.println(id + "번 명언이 존재하지 않습니다.");
                }
            }

            else if (cmd.startsWith("수정?id=")) {
                int id = Integer.parseInt(cmd.substring(6));

                if(map.get(id) != null) {
                    String[] str = map.get(id);
                    System.out.println("명언(기존) : " + str[0]);
                    System.out.print("명언 : ");
                    String newContent = sc.nextLine();
                    System.out.println("작가(기존) : " + str[1]);
                    System.out.print("작가 : ");
                    String newAuthor = sc.nextLine();
                    map.put(id, new String[] {newContent, newAuthor});
                }
                else {
                    System.out.println(id + "번 명언이 존재하지 않습니다.");
                }
            }
        }


    }
}