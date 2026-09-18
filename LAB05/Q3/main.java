abstract class Media {

    public String media_name;
    public int media_id;
    public int late_days;

    public Media(String n, int i, int d) {
        media_name = n;
        media_id = i;
        late_days = d;
    }
    public abstract float late_fee();
}

class Book extends Media {

    public Book(String name, int id, int days) {
        super(name, id, days);
    }

    public float late_fee() {
        return late_days * 2;
    }
}

class DVD extends Media {

    public DVD(String name, int id, int days) {
        super(name, id, days);
    }

    public float late_fee() {
        return late_days * 5;
    }
}

class Game extends Media {

    public Game(String name, int id, int days) {
        super(name, id, days);
    }

    public float late_fee() {
        return late_days*8;
    }
}

public class main {

    public static void main(String[] args) {

        Media[] returned_media = {new Book("Java Programming", 101, 3),new DVD("Avengers", 102, 2),new Game("Minecraft", 103, 5),
            new Book("Data Structures", 104, 4),new DVD("Interstellar", 105, 1)};

        float total_fee=0;

        for (Media media:returned_media) {

            float fee = media.late_fee();

            System.out.println("Media ID:" + media.media_id +",Name:" + media.media_name +",Late Days:" + media.late_days +",Late Fee:Rs." + fee);

            total_fee = total_fee + fee;
        }
        System.out.println("Total Late Fees:Rs." + total_fee);
    }
}

