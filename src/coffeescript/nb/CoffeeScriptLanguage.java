package coffeescript.nb;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.netbeans.api.lexer.InputAttributes;
import org.netbeans.api.lexer.Language;
import org.netbeans.api.lexer.LanguagePath;
import org.netbeans.api.lexer.Token;
import org.netbeans.spi.lexer.EmbeddingPresence;
import org.netbeans.spi.lexer.LanguageEmbedding;
import org.netbeans.spi.lexer.LanguageHierarchy;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;

/**
 * 
 * @author Denis Stepanov
 */
public class CoffeeScriptLanguage extends LanguageHierarchy<CoffeeScriptTokenId> {

    public static final String MIME_TYPE = "text/coffeescript"; //NOI18N
    private static final Language<CoffeeScriptTokenId> LANGUAGE = new CoffeeScriptLanguage().language();

    public static final Language<CoffeeScriptTokenId> getLanguage() {
        return LANGUAGE;
    }

    private CoffeeScriptLanguage() {
    }

    protected Collection<CoffeeScriptTokenId> createTokenIds() {
        return EnumSet.allOf(CoffeeScriptTokenId.class);
    }

    @Override
    protected Lexer<CoffeeScriptTokenId> createLexer(LexerRestartInfo<CoffeeScriptTokenId> lri) {
        return new CoffeeScriptLexer(lri);
    }

    @Override
    protected Map<String, Collection<CoffeeScriptTokenId>> createTokenCategories() {
        Map<String, Collection<CoffeeScriptTokenId>> map = new HashMap<String, Collection<CoffeeScriptTokenId>>();
        for (CoffeeScriptTokenId token : EnumSet.allOf(CoffeeScriptTokenId.class)) {
            Collection<CoffeeScriptTokenId> tokens = map.get(token.primaryCategory());
            if (tokens == null) {
                tokens = new HashSet<CoffeeScriptTokenId>();
                map.put(token.primaryCategory(), tokens);
            }
            tokens.add(token);
        }
        return map;
    }

    @Override
    protected EmbeddingPresence embeddingPresence(CoffeeScriptTokenId id) {
        if (id == CoffeeScriptTokenId.STRING_LITERAL) {
            return EmbeddingPresence.ALWAYS_QUERY;
        }
        if (id == CoffeeScriptTokenId.HEREGEX) {
            return EmbeddingPresence.ALWAYS_QUERY;
        }
        return null;
    }

    @Override
    protected LanguageEmbedding<?> embedding(Token<CoffeeScriptTokenId> token, LanguagePath languagePath, InputAttributes inputAttributes) {
        if (token.id() == CoffeeScriptTokenId.STRING_LITERAL) {
            return LanguageEmbedding.create(CoffeeScriptStringLanguage.getLanguage(), 0, 0);
        }
        if (token.id() == CoffeeScriptTokenId.HEREGEX) {
            return LanguageEmbedding.create(CoffeeScriptRegexpLanguage.getLanguage(), 0, 0);
        }
        return null;
    }

    @Override
    protected String mimeType() {
        return MIME_TYPE;
    }
}