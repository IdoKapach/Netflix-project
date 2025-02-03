package com.example.targil4.api;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

public class UriRequestBody extends RequestBody {
    private final Context context;
    private final Uri uri;
    private final String contentType;

    public UriRequestBody(Context context, Uri uri, String contentType) {
        this.context = context;
        this.uri = uri;
        this.contentType = contentType;
    }

    @Override
    public MediaType contentType() {
        return MediaType.parse(contentType);
    }

    @Override
    public long contentLength() throws IOException {
        // Attempt to get the file size using an AssetFileDescriptor.
        AssetFileDescriptor afd = context.getContentResolver().openAssetFileDescriptor(uri, "r");
        long length = afd.getLength();
        afd.close();
        return length;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        if (inputStream == null) {
            throw new IOException("Unable to open InputStream for URI: " + uri);
        }

        Source source = null;
        try {
            source = Okio.source(inputStream);
            sink.writeAll(source);
        } finally {
            if (source != null) {
                source.close();
            }
            inputStream.close();
        }
    }
}
