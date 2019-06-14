package com.main.zara;

import org.apache.log4j.Logger;

public class Constant {

    public String pathResources    = "/Users/werest/Documents/webapp_me/src/main/webapp/resourses/img/";
    String pathFLab4        = "Lab4/";

    String imgPath          = "Nature-259.jpg";
    String girlPretty       = "72651401.jpg";

    public String nomer            = "aSu3vTtF0Gc.jpg";
    public String nomer1           = "nomer1.png";
    public String nomer2           = "2-1.jpg";


    private final static Logger LOGGER = Logger.getLogger(Constant.class);


    void run(String prm){
        LOGGER.info(prm);
    }
}
