package com.meiling.drawimg.factory;

/**
 * 分割图片的工厂类
 * <p>
 * 2022-04-20 09:40
 *
 * @author marisareimu
 */
public class ImageSplitFactory {
    private ImageSplitInterface anInterface;

    public ImageSplitFactory(ImageSplitInterface anInterface) {
        this.anInterface = anInterface;
    }

    public ImageSplitInterface createImpl(){
        return anInterface;
    }
}
