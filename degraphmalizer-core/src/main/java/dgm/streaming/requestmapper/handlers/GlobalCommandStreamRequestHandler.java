package dgm.streaming.requestmapper.handlers;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.handler.codec.http.HttpRequest;
import dgm.streaming.blueprints.GraphCommandListener;
import dgm.streaming.blueprints.StreamingGraph;
import dgm.streaming.command.GraphCommand;
import dgm.streaming.requestmapper.HttpRequestMapper;
import dgm.streaming.requestmapper.RequestHandlerException;

/**
 * @author Ernst Bunders
 */
public class GlobalCommandStreamRequestHandler implements HttpRequestMapper.RequestHandler {

    public static final String PATH_REGEX = "^/global";
    private final StreamingGraph streamingGraph;

    public GlobalCommandStreamRequestHandler(StreamingGraph streamingGraph) {
        this.streamingGraph = streamingGraph;
    }

    @Override
    public final void handleRequest(HttpRequest request, Channel channel) throws RequestHandlerException {
        streamingGraph.addGraphCommandListener(new ChannelpushingGraphCommandListener(channel));
    }

    @Override
    public final String getPathMatchingExpression() {
        return PATH_REGEX;
    }

    public final class ChannelpushingGraphCommandListener implements GraphCommandListener {
        private final Channel channel;

        private ChannelpushingGraphCommandListener(Channel channel) {
            this.channel = channel;
        }

        @Override
        public void commandCreated(GraphCommand graphCommand) {
            channel.write(graphCommand);
        }

        public Channel getChannel(){
            return channel;
        }

    }
}
