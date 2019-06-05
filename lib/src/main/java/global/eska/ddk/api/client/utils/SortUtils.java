package global.eska.ddk.api.client.utils;

import global.eska.ddk.api.client.model.Sort;

public class SortUtils {

    public String[][] getSort(Sort[] sorts){
        String[][] arrSorts = new String[sorts.length][];
        for (int i = 0; i < sorts.length; i++) {
            arrSorts[i] = new String[]{sorts[0].getFieldName(), sorts[0].getSortDirection().toString()};
        }
        return arrSorts;
    }
}
