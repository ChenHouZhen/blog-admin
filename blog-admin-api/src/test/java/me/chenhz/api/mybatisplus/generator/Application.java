package me.chenhz.api.mybatisplus.generator;

public enum Application {
    ADMIN(new String[]{"me.chenhz.api","blog-admin-api"});

    private String[] path;

    private Application(String[] path){
        this.path = path;
    }

    public String[] getPath() {
        return path;
    }

    public void setPath(String[] path) {
        this.path = path;
    }

}
