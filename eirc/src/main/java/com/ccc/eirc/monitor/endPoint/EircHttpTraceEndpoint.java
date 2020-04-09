package com.ccc.eirc.monitor.endPoint;

import com.ccc.eirc.commons.annotation.EircEndPoint;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;

import java.util.List;

/**
 * <a>Title:EircHttpTraceEndpoint</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/28 17:33
 * @Version 1.0.0
 */
@EircEndPoint
public class EircHttpTraceEndpoint {

    private final HttpTraceRepository repository;

    public EircHttpTraceEndpoint(HttpTraceRepository repository) {
        this.repository = repository;
    }

    public EircHttpTraceDescriptor trace() {
        return new EircHttpTraceDescriptor(this.repository.findAll());
    }

    public static final class EircHttpTraceDescriptor {
        private final List<HttpTrace> traces;

        public EircHttpTraceDescriptor(List<HttpTrace> traces) {
            this.traces = traces;
        }

        public List<HttpTrace> getTraces() {
            return this.traces;
        }
    }
}
