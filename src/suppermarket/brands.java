/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suppermarket;


/**
 *
 * @author lukogo
 */
public class brands {
    
    int id;
    String brand, status;

    public brands(int id, String brand, String status) {
        this.id = id;
        this.brand = brand;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setStatus(String status) {
        this.status = status;
    }
   
    
}
