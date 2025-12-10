package com.ga.java.project01;

import java.util.ArrayList;
import java.util.List;

public class CardType {
    static String fileName = "card_types.csv";

    String cardTypeId;
    double withdrawLPD_Limit;
    double transferLPD_Limit;
    double transferLPDOwn_Limit;
    double depositLPD_Limit;
    double depositLPDOwn_Limit;

    public CardType(String cardTypeId, double withdrawLPD, double transferLPD, double transferOwn,
                    double depositLPD, double depositOwn){
        this.cardTypeId = cardTypeId;
        this.withdrawLPD_Limit = withdrawLPD;
        this.transferLPD_Limit = transferLPD;
        this.transferLPDOwn_Limit = transferOwn;
        this.depositLPD_Limit = depositLPD;
        this.depositLPDOwn_Limit = depositOwn;

        fileName = "card_types.csv";
    }

    public static void main(String[] args) {
        createCardType(new CardType("mastercard_platinum",
                20_000,
                40_000,
                80_000,
                100_000,
                200_000
                ));
        createCardType(new CardType("mastercard_titanium",
                10_000,
                20_000,
                40_000,
                100_000,
                200_000
        ));
        createCardType(new CardType("mastercard",
                5_000,
                10_000,
                20_000,
                100_000,
                200_000
        ));

    }

    protected static CardType getCardType(String id){
        List<CardType> types = getAllCardTypes();
        for(CardType c : types){
            if(c.cardTypeId.equalsIgnoreCase(id)) return c;
        }
        return null;
    }

    protected static boolean createCardType(CardType c){
        if(getCardType(c.cardTypeId) != null){
            System.out.println("CardType already exists");
            return false;
        }
        List<CardType> list = new ArrayList<>();
        list.add(c);
        CSVHandler.appendToCSVFile(fileName, toCSVData(list));
        return true;
    }

    protected boolean update(){
        List<CardType> types = getAllCardTypes();
        boolean found = false;
        for(int i = 0; i < types.size(); i++){
            if(types.get(i).cardTypeId.equalsIgnoreCase(this.cardTypeId)){
                types.set(i, this);
                found = true;
                break;
            }
        }
        if(found){
            CSVHandler handler = new CSVHandler();
            handler.writeToCSVFile(fileName, toCSVData(types));
        }
        return found;
    }

    protected boolean delete(){
        List<CardType> types = getAllCardTypes();
        boolean removed = types.removeIf(c -> c.cardTypeId.equalsIgnoreCase(this.cardTypeId));
        if(removed){
            CSVHandler handler = new CSVHandler();
            handler.writeToCSVFile(fileName, toCSVData(types));
        }
        return removed;
    }

    protected static List<CardType> getAllCardTypes(){
        List<List<String>> data = CSVHandler.readCSV(fileName);
        List<CardType> types = new ArrayList<>();
        if(data != null){
            for(List<String> row : data){
                types.add(new CardType(
                        row.get(0),
                        Double.parseDouble(row.get(1)),
                        Double.parseDouble(row.get(2)),
                        Double.parseDouble(row.get(3)),
                        Double.parseDouble(row.get(4)),
                        Double.parseDouble(row.get(5))
                ));
            }
        }
        return types;
    }

    protected static List<List<String>> toCSVData(List<CardType> types){
        List<List<String>> data = new ArrayList<>();
        for(CardType c : types){
            List<String> row = new ArrayList<>();
            row.add(c.cardTypeId);
            row.add(String.valueOf(c.withdrawLPD_Limit));
            row.add(String.valueOf(c.transferLPD_Limit));
            row.add(String.valueOf(c.transferLPDOwn_Limit));
            row.add(String.valueOf(c.depositLPD_Limit));
            row.add(String.valueOf(c.depositLPDOwn_Limit));
            data.add(row);
        }
        return data;
    }
}
