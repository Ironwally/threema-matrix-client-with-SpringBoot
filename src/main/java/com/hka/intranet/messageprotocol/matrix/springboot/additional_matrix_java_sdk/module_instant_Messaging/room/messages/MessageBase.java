package com.hka.intranet.messageprotocol.matrix.springboot.additional_matrix_java_sdk.module_instant_Messaging.room.messages;

import java.util.Objects;

import com.cosium.matrix_communication_client.Message;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Wrapper around SDK Message as constructor only accessible via builder. 
 * Not possible with extends.
 */
public class MessageBase {

  private final Message delegate;
  private final long timestamp;
  private final long id;

  protected MessageBase(Builder builder) {
    this.delegate = Message.builder()
        .body(builder.body)
        .format(builder.format)
        .formattedBody(builder.formattedBody)
        .type(builder.type)
        .build();
    this.timestamp = builder.timestamp;
    this.id = builder.id;
  }

  protected MessageBase(String body, String format, String formattedBody, String type, long timestamp, long id) {
    this.delegate = Message.builder()
        .body(body)
        .format(format)
        .formattedBody(formattedBody)
        .type(type)
        .build();
    this.timestamp = timestamp;
    this.id = id;
  }

  @JsonCreator
  public MessageBase(
      @JsonProperty("body") String body,
      @JsonProperty("format") String format,
      @JsonProperty("formatted_body") String formattedBody,
      @JsonProperty("msgtype") String type,
      @JsonProperty("timestamp") Long timestamp,
      @JsonProperty("id") Long id) {
    this.delegate = Message.builder()
        .body(body)
        .format(format)
        .formattedBody(formattedBody)
        .type(type)
        .build();
    this.timestamp = timestamp == null ? 0L : timestamp;
    this.id = id == null ? 0L : id;
  }
  
  @JsonProperty("body")
  public String body() { return delegate.body(); }
  @JsonProperty("format")
  public String format() { return delegate.format(); }
  @JsonProperty("formatted_body")
  public String formattedBody() { return delegate.formattedBody(); }
  @JsonProperty("msgtype")
  public String type() { return delegate.type(); }
  @JsonProperty("timestamp")
  public long timestamp() { return timestamp; }
  @JsonProperty("id")
  public long id() { return id; }

  /**
   * Access the original SDK {@link Message} for sending via existing APIs.
   */
  public Message toSdkMessage() { return delegate; }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof MessageBase that)) return false;
    return timestamp == that.timestamp && id == that.id && Objects.equals(delegate.body(), that.delegate.body())
        && Objects.equals(delegate.format(), that.delegate.format())
        && Objects.equals(delegate.formattedBody(), that.delegate.formattedBody())
        && Objects.equals(delegate.type(), that.delegate.type());
  }

  @Override
  public int hashCode() {
    return Objects.hash(delegate.body(), delegate.format(), delegate.formattedBody(), delegate.type(), timestamp, id);
  }

 public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    protected String body;
    protected String format;
    protected String formattedBody;
    protected String type;
    protected long timestamp;
    protected long id;

    private Builder() {
        format = "org.matrix.custom.html";
        timestamp = System.currentTimeMillis();
        id = 0L;
    }
    // constructor for extended classes
    protected Builder(Builder base) {
        this.format = base.format;
        this.timestamp = base.timestamp;
        this.id = base.id;
        this.type = base.type;
    }

    public Builder body(String body) { this.body = body; return this; }
    public Builder format(String format) { this.format = format; return this; }
    public Builder formattedBody(String formattedBody) { this.formattedBody = formattedBody; return this; }
    public Builder type(String type) { this.type = type; return this; }
    public Builder timestamp(long timestamp) { this.timestamp = timestamp; return this; }
    public Builder id(long id) { this.id = id; return this; }
    public MessageBase build() { return new MessageBase(this); }

    // New methods. First decide which type of message
    public MessageText.Builder text(String content) { return new MessageText.Builder(this).body(content); }
    public MessageImage.Builder image(String content, String url) { return new MessageImage.Builder(this).body(content).url(url); }
    public MessageFile.Builder file(String content, String url) { return new MessageFile.Builder(this).body(content).url(url); }
    public MessageAudio.Builder audio(String content, String url) { return new MessageAudio.Builder(this).body(content).url(url); }
    public MessageSticker.Builder sticker(String content) { return new MessageSticker.Builder(this).body(content); }
    public MessagePoll.Builder poll(String question, String[] answers) { return new MessagePoll.Builder(this).question(question).answers(answers); }
  }
}

