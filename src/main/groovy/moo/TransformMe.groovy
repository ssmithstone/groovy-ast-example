package moo


class TransformMe {


    @moo.TransformerAnn
    void wrapMeBefore(){
        println "Doing Something nice for the weekend ?"
    }

    public static void main(String[] args) {
        new TransformMe().wrapMeBefore();
    }
}
