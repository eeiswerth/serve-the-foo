package com.ericeiswerth.server.message;

/**
 * @author Eric Eiswerth
 */
public class Message {

    private String action;

    private String source;

    public Message() {
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("{action: ").append(action).append(", source: ").append(source).append("}");
        return sb.toString();
    }
}
