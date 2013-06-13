<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    version="1.0">
    
    <xsl:output method="text" encoding="UTF-8" />

    <!-- simply copy the message to the result tree -->
    <xsl:template match="/">
        <xsl:value-of select="message" />
    </xsl:template>
</xsl:stylesheet>