The self-signed test certificate was generated using the following command:

    openssl req -x509 -newkey rsa:4096 -nodes\
        -keyout private.key -out cert.pem -days 3650\
        -subj "/UID=urn:mrn:mcp:user:example:nikos/CN=Nikolaos Vastardis/O=TRINITY HOUSE/OU=GRAD/C=GB/ST=ESSEX/L=HARWICH"\
        -addext "basicConstraints=critical,CA:FALSE"\
        -addext "keyUsage=critical,digitalSignature,keyEncipherment"

Then we can extract the public certificate as:

    openssl x509 -in cert.pem -noout -pubkey > publicKey.pem

Finally to minify:

    openssl x509 -in cert.pem -outform DER | base64 -w 0 > minifiedCert.txt
    openssl pkey -pubin -in publicKey.pem -outform DER | base64 -w 0 > minifiedPublicKey.txt