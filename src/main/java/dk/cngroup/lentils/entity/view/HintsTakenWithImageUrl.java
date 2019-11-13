package dk.cngroup.lentils.entity.view;

import dk.cngroup.lentils.entity.HintTaken;

public class HintsTakenWithImageUrl {
    private HintTaken hintTaken;
    private String imageUrl;

    public HintsTakenWithImageUrl(final HintTaken hintTaken, final String imageUrl) {
        this.hintTaken = hintTaken;
        this.imageUrl = imageUrl;
    }

    public HintTaken getHintTaken() {
        return hintTaken;
    }

    public void setHintTaken(final HintTaken hintTaken) {
        this.hintTaken = hintTaken;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
