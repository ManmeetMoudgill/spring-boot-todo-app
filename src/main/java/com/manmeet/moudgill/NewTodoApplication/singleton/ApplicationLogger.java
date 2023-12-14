package com.manmeet.moudgill.NewTodoApplication.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationLogger {

    private static Logger logger;

    public static Logger getInstance(){

        if(logger==null){
            logger=LoggerFactory.getLogger(ApplicationLogger.class);
        }
        return logger;
    }
}
