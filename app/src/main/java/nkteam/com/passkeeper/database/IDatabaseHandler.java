package nkteam.com.passkeeper.database;

import java.util.List;

public interface IDatabaseHandler {

    public void addDataPass(PKDataModel pkDataModel);
    public PKDataModel getDataPass(int id);
    public List<PKDataModel> getAllDataPasses();
    public int updateDataPass(PKDataModel pkDataModel);
    public void deleteDataPass(PKDataModel pkDataModel);
    public void deleteAllDataPasses();

}
