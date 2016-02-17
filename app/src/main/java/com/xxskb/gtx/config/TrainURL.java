package com.xxskb.gtx.config;

/**
 * Created by renyufei on 16-2-16.
 */
public class TrainURL {
    public final static String BASE_URL = "https://kyfw.12306.cn";
    public final static String STATION_NAME_SCRIPT_URL = BASE_URL + "/otn/resources/js/framework/station_name.js?station_version=1.8902";
    public final static String TRIAN_NAME_SCRIPT_URL = BASE_URL + "";
    public final static String TRIAN_YUPIAO_URL = BASE_URL + "otn/leftTicket/queryT?leftTicketDTO.train_date={TRAIN_DATE}&leftTicketDTO.from_station={FROM_STATION}&leftTicketDTO.to_station={TO_STATION}&purpose_codes=ADULT";
}
