package dgm.streaming.service;

import dgm.streaming.blueprints.GraphCommandListener;

/**
 * @author Ernst Bunders
 */
public interface GraphUnfoldingService
{
    void unfoldVertex(String id, GraphCommandListener listener);
}

