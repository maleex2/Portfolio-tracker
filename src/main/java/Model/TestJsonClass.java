package Model;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/***
 * Just a wee example of how we gonna save classes as json.
 */

public class TestJsonClass {
  private int testInt;
  private String testString;
  private ArrayList<TestObject> testList;
  private HashMap<Integer,TestObject> testMap;

  public String toString(){
    String string=""+testInt+" "+testString+" ";
    for(TestObject t:testList){
      string+="[TestObject from list: "+t.test+" "+t.test2+"]";
    }
    for(Map.Entry<Integer,TestObject> entry: testMap.entrySet()){
      string+="{Key in map:"+entry.getKey()+"[TestObject from map: "+entry.getValue().test+" "+entry.getValue().test2+"]}";
    }
    return string ;
  }

  public static class TestObject{
    String test;
    int test2;
  }

  public static void main(String[] args) {
    TestJsonClass test=new TestJsonClass();
    test.testInt=1;
    test.testString="test";
    TestObject  testObject=new TestObject();
    testObject.test="Object";
    testObject.test2=1;
    test.testList=new ArrayList<>();
    test.testList.add(testObject);

    test.testMap=new HashMap<>();
    test.testMap.put(1,testObject);



    //Create a new Gon object
    Gson gson= new Gson();

    //convert ant object to json string
    String json=gson.toJson(test);

    //this should print out how the json string should look:
    //should be like this: {"testInt":1,"testString":"test","testList":[{"test":"Object","test2":1}],"testMap":{"1":{"test":"Object","test2":1}}}
    System.out.println(json);

    //convert any json string to object
    TestJsonClass test2= gson.fromJson(json,TestJsonClass.class);

    //this should print out the newly build Object from json string
    System.out.println(test2);
    System.out.println();

  }
}
