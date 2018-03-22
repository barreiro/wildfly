/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2017, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.wildfly.extension.agroal.operation;

import org.jboss.as.controller.AbstractBoottimeAddStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.server.AbstractDeploymentChainStep;
import org.jboss.as.server.DeploymentProcessorTarget;
import org.jboss.as.server.deployment.Phase;
import org.jboss.dmr.ModelNode;
import org.wildfly.extension.agroal.AgroalExtension;
import org.wildfly.extension.agroal.deployment.DataSourceDefinitionAnnotationProcessor;
import org.wildfly.extension.agroal.logging.AgroalLogger;

/**
 * Handler responsible for adding the subsystem resource to the model
 *
 * @author <a href="lbarreiro@redhat.com">Luis Barreiro</a>
 */
public class AgroalSubsystemAdd extends AbstractBoottimeAddStepHandler {

    public static final AgroalSubsystemAdd INSTANCE = new AgroalSubsystemAdd();

    private AgroalSubsystemAdd() {
    }

    @Override
    protected void performBoottime(OperationContext context, ModelNode operation, ModelNode model) throws OperationFailedException {
        context.addStep( new AbstractDeploymentChainStep() {
            public void execute(DeploymentProcessorTarget processorTarget) {
                AgroalLogger.SERVICE_LOGGER.infof( "Adding deployment processor for DataSourceDefinition annotation" );
                processorTarget.addDeploymentProcessor( AgroalExtension.SUBSYSTEM_NAME, Phase.PARSE, Phase.PARSE_RESOURCE_DEF_ANNOTATION_DATA_SOURCE, new DataSourceDefinitionAnnotationProcessor() );
            }
        }, OperationContext.Stage.RUNTIME );
    }

    @Override
    protected void populateModel(ModelNode operation, ModelNode model) throws OperationFailedException {
        // TODO:
    }
}
