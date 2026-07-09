/*
 *
 * Copyright 2026 Florian Schmaus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jivesoftware.smack.tcp;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.IntConsumer;

class CallbackInputStream extends FilterInputStream {
    private final IntConsumer onReadCallback;

    CallbackInputStream(InputStream in, IntConsumer onReadCallback) {
        super(in);
        this.onReadCallback = onReadCallback != null ? onReadCallback : (bytes) -> { };
    }

    @Override
    public int read() throws IOException {
        int b = super.read();
        if (b != -1) {
            onReadCallback.accept(1);
        }
        return b;
    }

    @Override
    public int read(byte[] b) throws IOException {
        int bytesRead = super.read(b);
        if (bytesRead > 0) {
            onReadCallback.accept(bytesRead);
        }
        return bytesRead;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int bytesRead = super.read(b, off, len);
        if (bytesRead > 0) {
            onReadCallback.accept(bytesRead);
        }
        return bytesRead;
    }
}
