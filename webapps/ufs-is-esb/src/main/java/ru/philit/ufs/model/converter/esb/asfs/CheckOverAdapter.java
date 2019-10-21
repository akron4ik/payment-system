package ru.philit.ufs.model.converter.esb.asfs;

import ru.philit.ufs.model.entity.esb.asfs.LimitStatusType;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRs;
import ru.philit.ufs.model.entity.oper.CheckOverLimit;
import ru.philit.ufs.model.entity.oper.LimitStatusTypeCode;

public class CheckOverAdapter extends AsfsAdapter {

    private static LimitStatusTypeCode limitType(LimitStatusType limitStatusType){
        return (limitStatusType != null) ? LimitStatusTypeCode.getByCode(limitStatusType.value()) : null;
    }

    private static void map(CheckOverLimit checkOverLimit, SrvCheckOverLimitRq.SrvCheckOverLimitRqMessage message) {
        message.setAmount(checkOverLimit.getAmount());
        message.setTobeIncreased(checkOverLimit.isTobeIncreased());
        message.setUserLogin(checkOverLimit.getUserLogin());
    }

    private static void map(SrvCheckOverLimitRs.SrvCheckOverLimitRsMessage message, CheckOverLimit checkOverLimit) {
        checkOverLimit.setResponseCode(message.getResponseCode());
        checkOverLimit.setLimitStatusTypeCode(limitType(message.getStatus()));
    }

    public static SrvCheckOverLimitRq requestCheckLimit(CheckOverLimit checkOverLimit) {
        SrvCheckOverLimitRq request = new SrvCheckOverLimitRq();
        request.setHeaderInfo(headerInfo());
        request.setSrvCheckOverLimitRqMessage(new SrvCheckOverLimitRq.SrvCheckOverLimitRqMessage());
        map(checkOverLimit, request.getSrvCheckOverLimitRqMessage());
        return request;
    }

    public static CheckOverLimit convert(SrvCheckOverLimitRs response) {
        CheckOverLimit checkOverLimit = new CheckOverLimit();
        map(response.getSrvCheckOverLimitRsMessage(), checkOverLimit);
        return checkOverLimit;
    }



}
