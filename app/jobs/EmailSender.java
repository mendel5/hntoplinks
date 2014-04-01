package jobs;

import models.Item;
import play.Play;
import play.db.jpa.NoTransaction;
import play.jobs.Every;
import play.jobs.Job;
import play.jobs.On;
import play.vfs.VirtualFile;
import utils.Templater;

import java.util.*;

/**
 * User: eguller
 * Date: 3/31/14
 * Time: 7:03 AM
 */

@On("0 0 1 ? * *")
public class EmailSender extends Job{
    private static final
    int previousDay = -1;
    int previousWeek = -1;
    int sentEmailCount = 0;
    @Override @NoTransaction
    public void doJob(){

        String sender = Play.configuration.getProperty("mail.smtp.user");
    }

    public void sendEmail(String to){

    }

    public static String createHtml(String subscriptionId, List<Item> itemList){
        String itemContent = templateItems(subscriptionId, itemList);
        Map<String, String> values = new HashMap<String, String>();
        values.put("items", itemContent);
        values.put("time", "Yesterday");
        values.put("subscriptionid", subscriptionId);
        String content = VirtualFile.fromRelativePath("/app/template/email.html").contentAsString();
        String templated = Templater.template(content, values);
        return templated;
    }

    private static String templateItems(String subscriptionId, List<Item> itemList) {
        String content = VirtualFile.fromRelativePath("/app/template/item.html").contentAsString();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < itemList.size(); i ++){
            Item item = itemList.get(i);
            Map<String, String> values = new HashMap<String, String>();
            values.put("url", item.getUrl());
            values.put("index", String.valueOf(i + 1));
            values.put("title", item.getTitle());
            values.put("comhead", item.getComhead());
            values.put("userid", item.getUser());
            values.put("username", item.getUser());
            values.put("points", String.valueOf(item.getPoints()));
            values.put("comment", String.valueOf(item.getComment()));
            values.put("hnid", String.valueOf(item.getHnid()));
            String templated = Templater.template(content, values);
            sb.append(templated);
        }
        return sb.toString();
    }

    private static String createText(List<Item> itemList){
        return null;
    }

}
