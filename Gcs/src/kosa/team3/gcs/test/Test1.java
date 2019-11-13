package kosa.team3.gcs.test;

import org.json.JSONArray;
import org.json.JSONObject;

public class Test1 {
    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key1", "value1");
        jsonObject.put("key2", "value2");
        jsonObject.put("key3", "value3");
        System.out.println(jsonObject.toString());
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);
        System.out.println(jsonArray.toString());
        System.out.println(jsonArray.length());
        String str = "[{'key1':0}, 2, 3]";
        JSONArray jsonArray2 = new JSONArray(str);
        System.out.println(jsonArray2.toString());
        JSONObject jsonObject2 = jsonArray2.getJSONObject(0);
        System.out.println(jsonObject2.getInt("key1"));
    }
}
