public class Driver{
    public static void main(String[] args){
        Point[] points = new Point[5];
        points[0] = new Point(1, 2);
        points[1] = new Point(3, 4);
        points[2] = new Point(1, 2);
        points[3] = new Point(5, 6);
        points[4] = new Point(7, 8);
        int distinctCount = 0;
        for (int i=0;i<points.length;i++){
            boolean alreadyExists=false;
            for (int j=0;j<i;j++) {
                if (points[i].equals(points[j])){
                    alreadyExists=true;
                    break;
                }
            }
            if (!alreadyExists){
                distinctCount++;
            }
        }
        System.out.println("Distinct: " + distinctCount);
    }
}
