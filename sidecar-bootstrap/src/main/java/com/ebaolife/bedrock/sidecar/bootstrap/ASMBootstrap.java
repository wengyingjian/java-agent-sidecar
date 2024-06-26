package com.ebaolife.bedrock.sidecar.bootstrap;

import com.ebaolife.bedrock.sidecar.common.config.Config;
import com.ebaolife.bedrock.sidecar.common.config.ProfilingConfig;
import com.ebaolife.bedrock.sidecar.common.util.Logger;
import com.ebaolife.bedrock.sidecar.metric.core.AbstractBootstrap;
import com.ebaolife.bedrock.sidecar.metric.core.recorder.AbstractRecorderMaintainer;
import com.ebaolife.bedrock.sidecar.bootstrap.aop.ProfilingAspect;


/**
 * Created by LinShunkang on 2018/4/19
 */
public final class ASMBootstrap extends AbstractBootstrap {

    private static final ASMBootstrap instance = new ASMBootstrap();

    private ASMBootstrap() {
        //empty
    }

    public static ASMBootstrap getInstance() {
        return instance;
    }

    @Override
    public AbstractRecorderMaintainer doInitRecorderMaintainer() {
        Config.RecorderConfig recorderConf = ProfilingConfig.recorderConfig();
        ASMRecorderMaintainer maintainer = ASMRecorderMaintainer.getInstance();
        if (maintainer.initial()) {
            return maintainer;
        }
        return null;
    }

    @Override
    public boolean initOther() {
        return initProfilerAspect();
    }

    private boolean initProfilerAspect() {
        try {
            ProfilingAspect.setRecorderMaintainer((ASMRecorderMaintainer) maintainer);
            ProfilingAspect.setRunning(true);
            return true;
        } catch (Exception e) {
            Logger.error("ASMBootstrap.initProfilerAspect()", e);
        }
        return false;
    }
}
