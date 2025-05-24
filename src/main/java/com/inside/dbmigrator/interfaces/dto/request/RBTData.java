package com.inside.dbmigrator.interfaces.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RBTData {
    private String month;
    private String contentId;
    private String vasCode;
    private String subType;
    private String validity;
    private String tarif;
    private String trx;
    private String revenue;
    private String channel;
    private String cp;
    private String title;
    private String singer;
}
