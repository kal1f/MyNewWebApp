package wrapper;


import util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

public class CachedHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private final byte[] cachedBody;

    public CachedHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        InputStream requestInputStream = request.getInputStream();
        this.cachedBody = StreamUtils.copyToByteArray(requestInputStream);

    }

    @Override
    public BufferedReader getReader() throws IOException{
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cachedBody);
        return new BufferedReader(new InputStreamReader(byteArrayInputStream));
    }

}
