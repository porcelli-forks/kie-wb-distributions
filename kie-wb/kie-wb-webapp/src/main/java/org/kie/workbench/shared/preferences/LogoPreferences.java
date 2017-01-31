/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.workbench.shared.preferences;

import org.uberfire.preferences.shared.annotations.Property;
import org.uberfire.preferences.shared.annotations.WorkbenchPreference;
import org.uberfire.preferences.shared.bean.BasePreference;

@WorkbenchPreference(identifier = "LogoPreferences",
        bundleKey = "LogoPreferences.Label")
public class LogoPreferences implements BasePreference<LogoPreferences> {

    @Property(bundleKey = "LogoPreferences.LogoUrl")
    private String logoURL;

    @Property(bundleKey = "LogoPreferences.TextAlign")
    private String textAlign;

    @Property(bundleKey = "LogoPreferences.Width")
    private String width;

    @Property(bundleKey = "LogoPreferences.Height")
    private String height;

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL( final String logoURL ) {
        this.logoURL = logoURL;
    }

    public String getTextAlign() {
        return textAlign;
    }

    public void setTextAlign( final String textAlign ) {
        this.textAlign = textAlign;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth( final String width ) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight( final String height ) {
        this.height = height;
    }

    @Override
    public LogoPreferences defaultValue( final LogoPreferences defaultValue ) {
        defaultValue.setTextAlign( "center" );
        defaultValue.setLogoURL( "http://uberfireframework.org/upload/images/uberfire-logo.png" );
        defaultValue.setWidth( "100px" );
        defaultValue.setHeight( "100px" );

        return defaultValue;
    }
}
