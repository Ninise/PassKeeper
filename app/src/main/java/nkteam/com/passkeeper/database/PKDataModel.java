package nkteam.com.passkeeper.database;

public class PKDataModel {

    private int _id;
    private String _url;
    private String _login;
    private String _pass;
    private String _email;
    private String _extra;

    public PKDataModel() { }

    public PKDataModel(String _url, String _login, String _pass, String _email, String _extra) {
        this._url = _url;
        this._login = _login;
        this._pass = _pass;
        this._email = _email;
        this._extra = _extra;
    }

    public PKDataModel(int _id, String _url, String _login, String _pass, String _email, String _extra) {
        this._id = _id;
        this._url = _url;
        this._login = _login;
        this._pass = _pass;
        this._email = _email;
        this._extra = _extra;
    }


    public String getUrl() { return _url; }

    public void setUrl(String _url) { this._url = _url; }

    public int getId() { return _id; }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getLogin() {
        return _login;
    }

    public void setLogin(String _login) {
        this._login = _login;
    }

    public String getPass() {
        return _pass;
    }

    public void setPass(String _pass) {
        this._pass = _pass;
    }

    public String getEmail() {
        return _email;
    }

    public void setEmail(String _email) {
        this._email = _email;
    }

    public String getExtra() {
        return _extra;
    }

    public void setExtra(String _extra) {
        this._extra = _extra;
    }

}
