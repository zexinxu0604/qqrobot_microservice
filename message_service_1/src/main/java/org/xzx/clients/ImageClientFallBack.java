package org.xzx.clients;

import org.springframework.stereotype.Component;
import org.xzx.pojo.Image.ImageResponse;

@Component
public class ImageClientFallBack implements ImageClient{

    @Override
    public ImageResponse getRandomImage() {
        return null;
    }

    @Override
    public int checkUrl(String url) {
        return 1;
    }

    @Override
    public boolean insertImage(String url) {
        return false;
    }

    @Override
    public boolean deleteImage(String url) {
        return false;
    }

    @Override
    public boolean restoreImage(String url) {
        return false;
    }

}
