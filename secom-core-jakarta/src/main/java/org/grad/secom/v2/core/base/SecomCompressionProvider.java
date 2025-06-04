/*
 * Copyright (c) 2022 GLA Research and Development Directorate
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.grad.secom.v2.core.base;

import org.grad.secom.v2.core.models.enums.CompressionAlgorithmEnum;

/**
 * The SECOM Compression Provider Interface.
 *
 * This interface dictates the implementation of the SECOM compression provider.
 * This is required for the SECOM library to be able to automatically compress
 * the payloads of the appropriate messages (e.g. get objects).
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface SecomCompressionProvider {

    /**
     * Returns the compression algorithm for the compression provider.
     * In SECOM, by default this should be ZIP.
     *
     * @return the compression algorithm for the compression provider
     */
    default CompressionAlgorithmEnum getCompressionAlgorithm() {
        return CompressionAlgorithmEnum.ZIP;
    }

    /**
     * The compression function. It simply requires the compression algorithm and
     * the payload that will be compressed. The final result will be returned as
     * a byte array.
     *
     * @param compressionAlgorithm  The algorithm to be used for the compression operation
     * @param payload               The payload to be compressed
     * @return The compressed data
     */
    byte[] compress(CompressionAlgorithmEnum compressionAlgorithm, byte[] payload);

    /**
     * The decompression operation. This should be provided the compression
     * algorithm used as well as the data to be decompressed. The final result
     * will be returned as a byte array.
     *
     * @param compressionAlgorithm  The algorithm used for the compression
     * @param data                  The compressed data
     * @return the decompressed result
     */
    byte[] decompress(CompressionAlgorithmEnum compressionAlgorithm, byte[] data);

}
