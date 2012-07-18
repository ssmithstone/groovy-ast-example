package moo


class TransformMe {


    @TransformerAnn
    void wrapMeBefore(){
        println "Doing Something nice for the weekend ?"
    }

    public static void main(String[] args) {
        new TransformMe().wrapMeBefore();
    }
}
