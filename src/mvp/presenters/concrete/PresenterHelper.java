package mvp.presenters.concrete;

import gui.overlays.ContactInfo;
import mvp.model.abs.TelegramModel;
import org.javagram.dao.KnownPerson;
import org.javagram.dao.Person;

import java.awt.image.BufferedImage;

/**
 * Created by HerrSergio on 31.08.2017.
 */
public class PresenterHelper {
    public static String getPersonName(Person person) {
        return person.getFirstName() + " " + person.getLastName();
    }

    public static Person getPerson(TelegramModel model, Integer id) {
        if(id == null)
            return null;
        else
            return model.getPerson(id);
    }

    public static Person getSelectedPerson(TelegramModel model) {
        return getPerson(model, model.getSelectedContactId());
    }

    public static BufferedImage getPhoto(TelegramModel telegramProxy, Person person, boolean small) {
        BufferedImage image;

        try {
            image = telegramProxy.getPhoto(person, small);
        } catch (Exception e) {
            e.printStackTrace();
            image = null;
        }

        return image;//getPhoto(image, small);
    }

    /*private static BufferedImage getPhoto(BufferedImage image, boolean small) {
        if(image == null)
            image = Images.getUserImage(small);
        return image;
    }*/

    public static ContactInfo toContactInfo(KnownPerson person, TelegramModel model, boolean small) {//, boolean makeCircle) {
        ContactInfo info = toContactInfo(person);
        if(model != null)
            info.setPhoto(getPhoto(model, person, small));
        return info;
    }


    public static ContactInfo toContactInfo(KnownPerson person) {
        return new ContactInfo(person.getPhoneNumber(), person.getFirstName(), person.getLastName(), person.getId());
    }
}
