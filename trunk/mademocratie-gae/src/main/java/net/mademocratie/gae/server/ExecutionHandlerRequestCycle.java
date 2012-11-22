package net.mademocratie.gae.server;

import net.mademocratie.gae.client.ExceptionPage;
import org.apache.wicket.Page;
import org.apache.wicket.core.request.handler.PageProvider;
import org.apache.wicket.core.request.handler.RenderPageRequestHandler;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.IRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * ExecutionHandlerRequestCycle
 */
public class ExecutionHandlerRequestCycle implements IRequestCycleListener {
    public ExecutionHandlerRequestCycle(MaDemocratieApp maDemocratieApp) {

    }

    @Override
    public void onBeginRequest(RequestCycle requestCycle) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onEndRequest(RequestCycle requestCycle) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onDetach(RequestCycle requestCycle) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onRequestHandlerResolved(RequestCycle requestCycle, IRequestHandler iRequestHandler) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onRequestHandlerScheduled(RequestCycle requestCycle, IRequestHandler iRequestHandler) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public IRequestHandler onException(RequestCycle requestCycle, Exception e) {
        Page page =null;
        if(e instanceof RuntimeException) {
            page = new ExceptionPage(new PageParameters(),(RuntimeException)e);
        }
        return new RenderPageRequestHandler(new PageProvider(page));
    }

    @Override
    public void onExceptionRequestHandlerResolved(RequestCycle requestCycle, IRequestHandler iRequestHandler, Exception e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onRequestHandlerExecuted(RequestCycle requestCycle, IRequestHandler iRequestHandler) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onUrlMapped(RequestCycle requestCycle, IRequestHandler iRequestHandler, Url url) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
