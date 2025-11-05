package org.com;

import java.util.Scanner;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("== 명언 앱 ==");
        LinkedHashMap<Integer, WiseSaying> wiseSayings = new LinkedHashMap<>();
        int lastId = 0;

        Scanner sc = new Scanner(System.in);


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
                wiseSayings.put(++lastId, new WiseSaying(lastId, content, author));

                System.out.println(lastId + "번 명언이 등록되었습니다.");
            }

            else if (cmd.equals("목록")) {
                System.out.println("번호 / 작가 / 명연");
                System.out.println("-----------------");
                for(Integer key : wiseSayings.keySet()) {
                    WiseSaying ws = wiseSayings.get(key);
                    System.out.println("%d / %s / %s".formatted(ws.getId(), ws.getAuthor(), ws.getContent()));
                }

            }

            else if (cmd.startsWith("삭제?id=")) {
                int id = Integer.parseInt(cmd.substring(6));
                if(wiseSayings.get(id) != null) {
                    wiseSayings.remove(id);
                    System.out.println(id + "번 명언이 삭제되었습니다.");
                }
                else {
                    System.out.println(id + "번 명언이 존재하지 않습니다.");
                }
            }

            else if (cmd.startsWith("수정?id=")) {
                int id = Integer.parseInt(cmd.substring(6));
                WiseSaying ws = wiseSayings.get(id);
                if(ws != null) {
                    System.out.println("명언(기존) : " + ws.getContent());
                    System.out.print("명언 : ");
                    String newContent = sc.nextLine();
                    System.out.println("작가(기존) : " + ws.getAuthor());
                    System.out.print("작가 : ");
                    String newAuthor = sc.nextLine();
                    wiseSayings.put(id, new WiseSaying(id, newContent, newAuthor));
                }
                else {
                    System.out.println(id + "번 명언이 존재하지 않습니다.");
                }


            }
        }
    }


}