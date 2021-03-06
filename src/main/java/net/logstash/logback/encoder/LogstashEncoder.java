/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
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
package net.logstash.logback.encoder;

import static org.apache.commons.io.IOUtils.LINE_SEPARATOR;
import static org.apache.commons.io.IOUtils.write;

import java.io.IOException;

import net.logstash.logback.LogstashFormatter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.encoder.EncoderBase;

public class LogstashEncoder extends EncoderBase<ILoggingEvent> {
    
    private boolean immediateFlush = true;
    
    private final LogstashFormatter formatter = new LogstashFormatter();
    
    @Override
    public void doEncode(ILoggingEvent event) throws IOException {
        
        write(formatter.writeValueAsBytes(event, getContext()), outputStream);
        write(CoreConstants.LINE_SEPARATOR, outputStream);
        
        if (immediateFlush) {
            outputStream.flush();
        }
        
    }
    
    @Override
    public void close() throws IOException {
        write(LINE_SEPARATOR, outputStream);
    }
    
    public boolean isImmediateFlush() {
        return immediateFlush;
    }
    
    public void setImmediateFlush(boolean immediateFlush) {
        this.immediateFlush = immediateFlush;
    }
    
    public boolean isIncludeCallerInfo() {
        return formatter.isIncludeCallerInfo();
    }
    
    public void setIncludeCallerInfo(boolean includeCallerInfo) {
        formatter.setIncludeCallerInfo(includeCallerInfo);
    }
    
    public void setCustomFields(String customFields) {
        formatter.setCustomFieldsFromString(customFields, this);
    }
    
    public String getCustomFields() {
        return formatter.getCustomFields().toString();
    }
    
}
