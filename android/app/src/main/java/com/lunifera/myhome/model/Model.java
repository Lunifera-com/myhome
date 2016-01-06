package com.lunifera.myhome.model;


import java.util.List;

/**
 * Created by bernhardedler on 08.12.15.
 */
public class Model{

    private String name;
    private String id;
    private List<Endpoint> endpoints;

    public Model(){

    }

    public Model(String name, String id, List<Endpoint> endpoints) {
        this.name = name;
        this.id = id;
        this.endpoints = endpoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Endpoint> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(List<Endpoint> endpoints) {
        this.endpoints = endpoints;
    }
}




