package org.com;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.*;
import java.io.*;
import java.nio.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {
    private static final String BASE_PATH = "C:\\Users\\KIMHS\\Desktop\\devcourse\\db\\wiseSaying";

    public static void main(String[] args) throws IOException, ParseException {
        System.out.println("== 명언 앱 ==");
        HashMap<Integer, String[]> map = new HashMap<>();
        Scanner sc = new Scanner(System.in);
        int lastId = 0;


        // 디렉토리 생성
        File dir = new File(BASE_PATH);
        if(!dir.exists()) {
            dir.mkdirs();
        }

        File lastIdFile = new File(BASE_PATH + "\\lastId.txt");
        boolean lastIdFileCreated = lastIdFile.createNewFile();

        if(!lastIdFileCreated) {
            // 파일 값 가져옴
            BufferedReader reader = new BufferedReader(new FileReader(BASE_PATH + "\\lastId.txt"));
            String str;
            while((str = reader.readLine()) != null) {
                lastId = Integer.parseInt(str);
            }
        }

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
                System.out.println(++lastId + "번 명언이 등록되었습니다.");

                JSONObject obj = new JSONObject();
                obj.put("id", lastId);
                obj.put("content", content);
                obj.put("author", author);

                // 파일 생성
                try {
                    File f = new File(BASE_PATH + "\\" + lastId + ".json");
                    f.createNewFile();

                    FileWriter fw = new FileWriter(f);
                    fw.write(obj.toJSONString());
                    fw.flush();
                    fw.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                // lastIdFile에 lastId로 갱신
                FileWriter fileWriter = new FileWriter(BASE_PATH + "\\lastId.txt", false);
                fileWriter.write(String.valueOf(lastId));
                fileWriter.close();
            }

            if (cmd.equals("목록")) {
                System.out.println("번호 / 작가 / 명연");
                System.out.println("-----------------");

                for(int i = 1; i <= lastId; i++) {
                    JSONParser parser = new JSONParser();
                    try {
                        FileReader fr = new FileReader(BASE_PATH + "\\" + i +".json");
                        JSONObject jsonObject = (JSONObject) parser.parse(fr);

                        Long id = (Long) jsonObject.get("id");
                        String content = (String) jsonObject.get("content");
                        String author = (String) jsonObject.get("author");
                        System.out.println( id + " / " + author + " / " + content);
                    } catch (FileNotFoundException e) {
                        // 삭제된 파일은 무시
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (cmd.startsWith("삭제?id=")) {
                int id = Integer.parseInt(cmd.substring(6));

                File file = new File(BASE_PATH + "\\" + id + ".json");
                if(file.exists()) {
                    if(file.delete()) {
                        System.out.println(id + "번 명언이 삭제되었습니다.");
                    }
                }
                else {
                    System.out.println(id + "번 명언이 존재하지 않습니다.");
                }
            }

            if (cmd.startsWith("수정?id=")) {
                int id = Integer.parseInt(cmd.substring(6));
                File file = new File(BASE_PATH + "\\" + id + ".json");
                JSONParser parser = new JSONParser();

                if(file.exists()) {
                    FileReader fr = new FileReader(BASE_PATH + "\\" + id + ".json");

                    JSONObject jsonObject = (JSONObject) parser.parse(fr);
                    System.out.println("명언(기존) : " + jsonObject.get("content"));
                    System.out.print("명언 : ");
                    String newContent = sc.nextLine();
                    System.out.println("작가(기존) : " + jsonObject.get("author"));
                    System.out.print("작가 : ");
                    String newAuthor = sc.nextLine();

                    jsonObject.put("content", newContent);
                    jsonObject.put("author", newAuthor);

                    // 파일에 저장
                    try (FileWriter fw = new FileWriter(file)) {
                        fw.write(jsonObject.toJSONString());
                        fw.flush();
                    }

                }
                else {
                    System.out.println(id + "번 명언이 존재하지 않습니다.");
                }
            }

            // 9단계 완성

            if (cmd.equals("빌드")) {
                JSONArray jArray = new JSONArray();

                for(int i = 1; i <= lastId; i++) {
                    JSONParser parser = new JSONParser();
                    try {
                        FileReader fr = new FileReader(BASE_PATH + "\\" + i +".json");
                        JSONObject jsonObject = (JSONObject) parser.parse(fr);

                        Long id = (Long) jsonObject.get("id");
                        String content = (String) jsonObject.get("content");
                        String author = (String) jsonObject.get("author");

                        jsonObject.put("id", id);
                        jsonObject.put("content", content);
                        jsonObject.put("author", author);

                        jArray.add(jsonObject);



                    } catch (FileNotFoundException e) {
                        // 삭제된 파일은 무시
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
                // data.json 만들어서 넣기
                try {
                    File f = new File(BASE_PATH + "\\" + "data.json");
                    if(!f.exists()) {
                        f.createNewFile();
                    }

                    FileWriter fw = new FileWriter(f);
                    fw.write(jArray.toJSONString());
                    fw.flush();
                    fw.close();

                    System.out.println("data.json 파일의 내용이 갱신되었습니다.");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}