/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reguest_parameters_pojos;


public class JsonParams {
    private String jsonObject;
    private String userService;
    private String keyService;
   private int compressLength;
 
 

    public int getCompressLength() {
        return compressLength;
    }

    public void setCompressLength(int compressLength) {
        this.compressLength = compressLength;
    }
    
    

    public JsonParams() {
    }

    public String getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(String jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getUserService() {
        return userService;
    }

    public void setUserService(String userService) {
        this.userService = userService;
    }

    public String getKeyService() {
        return keyService;
    }

    public void setKeyService(String keyService) {
        this.keyService = keyService;
    }

    
    
}
