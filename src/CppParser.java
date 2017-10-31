
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.parser.IScannerExtensionConfiguration;
import org.eclipse.cdt.core.dom.parser.c.ANSICParserExtensionConfiguration;
import org.eclipse.cdt.core.dom.parser.c.GCCParserExtensionConfiguration;
import org.eclipse.cdt.core.dom.parser.c.GCCScannerExtensionConfiguration;
import org.eclipse.cdt.core.dom.parser.c.ICParserExtensionConfiguration;
import org.eclipse.cdt.core.dom.parser.cpp.ANSICPPParserExtensionConfiguration;
import org.eclipse.cdt.core.dom.parser.cpp.GPPParserExtensionConfiguration;
import org.eclipse.cdt.core.dom.parser.cpp.GPPScannerExtensionConfiguration;
import org.eclipse.cdt.core.dom.parser.cpp.ICPPParserExtensionConfiguration;
import org.eclipse.cdt.core.parser.*;
import org.eclipse.cdt.internal.core.dom.parser.AbstractGNUSourceCodeParser;
import org.eclipse.cdt.internal.core.dom.parser.c.GNUCSourceParser;
import org.eclipse.cdt.internal.core.dom.parser.cpp.GNUCPPSourceParser;
import org.eclipse.cdt.internal.core.parser.scanner.CPreprocessor;

import java.util.HashMap;
import java.util.Map;

/**
 * 邱景 创建于 2017/1/24.
 * 用途：C++语法分析
 */
public class CppParser {
    private final static IParserLogService NULL_LOG = new NullLogService();

    public static IASTTranslationUnit parse(
            String file,
            ParserLanguage parserLanguage,
            boolean useGNUExtensions
    ) {
        IScanner scanner = null;
        scanner = createScanner(
                FileContent.create(file, T.readFile(file).toCharArray()),
                parserLanguage,
                ParserMode.COMPLETE_PARSE,
                createScannerInfo(useGNUExtensions)
        );

        AbstractGNUSourceCodeParser parser = null;
        if (parserLanguage == ParserLanguage.CPP) {
            ICPPParserExtensionConfiguration configuration = useGNUExtensions ?
                    new GPPParserExtensionConfiguration() :
                    new ANSICPPParserExtensionConfiguration();
            parser = new GNUCPPSourceParser(
                    scanner, ParserMode.COMPLETE_PARSE, NULL_LOG, configuration, null
            );
        } else {
            ICParserExtensionConfiguration configuration = useGNUExtensions ?
                    new GCCParserExtensionConfiguration() :
                    new ANSICParserExtensionConfiguration();
            parser = new GNUCSourceParser(
                    scanner, ParserMode.COMPLETE_PARSE, NULL_LOG, configuration, null
            );
        }

        return parser.parse();
    }

    private static IScanner createScanner(
            FileContent fileContent,
            ParserLanguage parserLanguage,
            ParserMode parserMode,
            IScannerInfo scannerInfo
    ) {
        IScannerExtensionConfiguration configuration =
                parserLanguage == ParserLanguage.C ?
                        GCCScannerExtensionConfiguration.getInstance(scannerInfo) :
                        GPPScannerExtensionConfiguration.getInstance(scannerInfo);
        return new CPreprocessor(
                fileContent, scannerInfo, parserLanguage, NULL_LOG, configuration, null
        );
    }

    public static ScannerInfo createScannerInfo(boolean useGNUExtensions) {
        return useGNUExtensions ? new ScannerInfo(getGnuMap()) : new ScannerInfo(getStdMap());
    }

    private static Map<String, String> getGnuMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("__GNUC__", "4");
        map.put("__GNUC_MINOR__", "7");
        map.put("__SIZEOF_SHORT__", "2");
        map.put("__SIZEOF_INT__", "4");
        map.put("__SIZEOF_LONG__", "8");
        map.put("__SIZEOF_POINTER__", "8");
        return map;
    }

    private static Map<String, String> getStdMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("__SIZEOF_SHORT__", "2");
        map.put("__SIZEOF_INT__", "4");
        map.put("__SIZEOF_LONG__", "8");
        map.put("__SIZEOF_POINTER__", "8");
        return map;
    }
}
