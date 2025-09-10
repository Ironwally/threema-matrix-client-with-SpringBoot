package com.hka.intranet.messageprotocol.matrix.springboot.additional_matrix_java_sdk.module_instant_Messaging.room.messages;

/**
 * Matrix m.image message
 */
public class MessageImage extends MessageBase {

    private final String url; // mxc:// URI or http fallback
    private final long size;
    private final String mimeType;

    protected MessageImage(Builder builder) {
        super(builder);
        this.url = builder.url;
        this.size = builder.size;
        this.mimeType = builder.mimeType;
    }

    public static final class Builder extends MessageBase.Builder {

        private String url;
        private long size;
        private String mimeType;

        Builder(MessageBase.Builder base) {
            super(base);
            this.type = "m.image";
            this.size = 0L;
            this.mimeType = "image/jpeg"; // default
        }

        public Builder url(String url) { this.url = url; return this; }
        public Builder size(long size) { this.size = size; return this; }
        public Builder mimeType(String mimeType) { this.mimeType = mimeType; return this; }
    
        @Override public Builder body(String body) { super.body(body); return this; }
        @Override public Builder format(String format) { super.format(format); return this; }
        @Override public Builder formattedBody(String formattedBody) { super.formattedBody(formattedBody); return this; }
        @Override public Builder timestamp(long timestamp) { super.timestamp(timestamp); return this; }
        @Override public Builder id(long id) { super.id(id); return this; }
        @Override public MessageImage build() { return new MessageImage(this); }
    }

}
