package ku.cs.shop.models;

import java.util.ArrayList;

public class BanList {

    private ArrayList<Ban> banList;

    public BanList() {
        banList = new ArrayList<>();
    }

    public ArrayList<Ban> getBanList() {
        return banList;
    }

    public void addBan(Ban ban) {
        banList.add(ban);
    }

    public void removeBan(String banID) {
        Ban ban = findID(banID);
        banList.remove(ban);
    }

    public Ban findID(String banID) {
        Ban ban = null;
        for (Ban temp : banList) {
            if (banID.equals(temp.getBanID())) {
                ban = temp;
                break;
            }
        }
        return ban;
    }

    public Ban findObjectID(String type, String objectID) {
        Ban ban = null;
        for (Ban temp : banList) {
            if (objectID.equals(temp.getObjectID()) && type.equals(temp.getType())) {
                ban = temp;
                break;
            }
        }
        return ban;
    }

    public int findNextID() {
        int i = 1;
        boolean idFound = false;
        if (banList.size() == 0) return 1;
        for (; i < banList.size(); i++) { // loop ids
            idFound = false; // set to false
            for (Ban temp : banList) { // search all items
                if (Integer.parseInt(temp.getBanID()) == i) { // if id exists
                    idFound = true; // found id
                    break; // go to next id
                }
            }
            if (!idFound) { // if there's no id
                return i; // return the id
            }
        }
        return ++i; // if 1-n ids are taken, send the next one
    }

    public String toCsv() {
        String result = "";
        for (Ban ban : banList) {
            result += ban.toCSV() + "\n";
        }
        return result;
    }
}
