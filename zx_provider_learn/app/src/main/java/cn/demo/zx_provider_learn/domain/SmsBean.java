package cn.demo.zx_provider_learn.domain;

/**
 * @author Administrator
 * @version $Rev$
 * @time ${DATA} 16:35
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 * Created by xun on 2017/3/13.
 */

public class SmsBean {
    public String address;
    public String date;
    public String read;
    public String body;
    public String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "SmsBean{" +
                "address='" + address + '\'' +
                ", date='" + date + '\'' +
                ", read='" + read + '\'' +
                ", body='" + body + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
