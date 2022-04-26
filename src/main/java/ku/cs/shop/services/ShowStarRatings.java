package ku.cs.shop.services;

public class ShowStarRatings implements ShowPicture{

    @Override
    public String showStarRatings(double rating) {
        String path = "";
        if (rating >= 5) {
            path = getClass().getResource("/marketimages/5stars.png").toExternalForm();
            return path;
        }else if (rating >= 4) {
            path = getClass().getResource("/marketimages/4stars.png").toExternalForm();
            return path;
        }else if (rating >= 3) {
            path = getClass().getResource("/marketimages/3stars.png").toExternalForm();
            return path;
        }else if (rating >= 2) {
            path = getClass().getResource("/marketimages/2stars.png").toExternalForm();
            return path;
        }else if (rating >= 1) {
            path = getClass().getResource("/marketimages/1stars.png").toExternalForm();
            return path;
        }else if (rating >= 0) {
            path = getClass().getResource("/marketimages/0stars.png").toExternalForm();
            return path;
        }
        return path;
    }
}
