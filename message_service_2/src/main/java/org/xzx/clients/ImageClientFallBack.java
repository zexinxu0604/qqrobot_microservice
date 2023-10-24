package org.xzx.clients;

import org.springframework.stereotype.Component;
import org.xzx.bean.Image.ImageResponse;

@Component
public class ImageClientFallBack implements ImageClient{

    @Override
    public ImageResponse getRandomImage() {
        return null;
    }

    @Override
    public int checkUrl(String url, long poster, long groupid) {
        return 1;
    }

    @Override
    public boolean insertImage(String url, long poster, long groupid) {
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
