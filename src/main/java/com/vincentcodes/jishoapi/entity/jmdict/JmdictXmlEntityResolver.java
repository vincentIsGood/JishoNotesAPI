package com.vincentcodes.jishoapi.entity.jmdict;

import javax.xml.stream.XMLResolver;
import javax.xml.stream.XMLStreamException;
import java.util.HashMap;
import java.util.Map;

public class JmdictXmlEntityResolver implements XMLResolver {

    private final Map<String, String> ENTITY_DEFINITIONS = new HashMap<>();
    {
        ENTITY_DEFINITIONS.put("hob", "Hokkaido-ben");
        ENTITY_DEFINITIONS.put("ksb", "Kansai-ben");
        ENTITY_DEFINITIONS.put("ktb", "Kantou-ben");
        ENTITY_DEFINITIONS.put("kyb", "Kyoto-ben");
        ENTITY_DEFINITIONS.put("kyu", "Kyuushuu-ben");
        ENTITY_DEFINITIONS.put("nab", "Nagano-ben");
        ENTITY_DEFINITIONS.put("osb", "Osaka-ben");
        ENTITY_DEFINITIONS.put("rkb", "Ryuukyuu-ben");
        ENTITY_DEFINITIONS.put("thb", "Touhoku-ben");
        ENTITY_DEFINITIONS.put("tsb", "Tosa-ben");
        ENTITY_DEFINITIONS.put("tsug", "Tsugaru-ben");
        ENTITY_DEFINITIONS.put("agric", "agriculture");
        ENTITY_DEFINITIONS.put("anat", "anatomy");
        ENTITY_DEFINITIONS.put("archeol", "archeology");
        ENTITY_DEFINITIONS.put("archit", "architecture, building");
        ENTITY_DEFINITIONS.put("art", "art, aesthetics");
        ENTITY_DEFINITIONS.put("astron", "astronomy");
        ENTITY_DEFINITIONS.put("audvid", "audio-visual");
        ENTITY_DEFINITIONS.put("aviat", "aviation");
        ENTITY_DEFINITIONS.put("baseb", "baseball");
        ENTITY_DEFINITIONS.put("biochem", "biochemistry");
        ENTITY_DEFINITIONS.put("biol", "biology");
        ENTITY_DEFINITIONS.put("bot", "botany");
        ENTITY_DEFINITIONS.put("Buddh", "Buddhism");
        ENTITY_DEFINITIONS.put("bus", "business");
        ENTITY_DEFINITIONS.put("chem", "chemistry");
        ENTITY_DEFINITIONS.put("Christn", "Christianity");
        ENTITY_DEFINITIONS.put("comp", "computing");
        ENTITY_DEFINITIONS.put("cryst", "crystallography");
        ENTITY_DEFINITIONS.put("ecol", "ecology");
        ENTITY_DEFINITIONS.put("econ", "economics");
        ENTITY_DEFINITIONS.put("elec", "electricity, elec. eng.");
        ENTITY_DEFINITIONS.put("electr", "electronics");
        ENTITY_DEFINITIONS.put("embryo", "embryology");
        ENTITY_DEFINITIONS.put("engr", "engineering");
        ENTITY_DEFINITIONS.put("ent", "entomology");
        ENTITY_DEFINITIONS.put("finc", "finance");
        ENTITY_DEFINITIONS.put("fish", "fishing");
        ENTITY_DEFINITIONS.put("food", "food, cooking");
        ENTITY_DEFINITIONS.put("gardn", "gardening, horticulture");
        ENTITY_DEFINITIONS.put("genet", "genetics");
        ENTITY_DEFINITIONS.put("geogr", "geography");
        ENTITY_DEFINITIONS.put("geol", "geology");
        ENTITY_DEFINITIONS.put("geom", "geometry");
        ENTITY_DEFINITIONS.put("go", "go (game)");
        ENTITY_DEFINITIONS.put("golf", "golf");
        ENTITY_DEFINITIONS.put("gramm", "grammar");
        ENTITY_DEFINITIONS.put("grmyth", "Greek mythology");
        ENTITY_DEFINITIONS.put("hanaf", "hanafuda");
        ENTITY_DEFINITIONS.put("horse", "horse-racing");
        ENTITY_DEFINITIONS.put("law", "law");
        ENTITY_DEFINITIONS.put("ling", "linguistics");
        ENTITY_DEFINITIONS.put("logic", "logic");
        ENTITY_DEFINITIONS.put("MA", "martial arts");
        ENTITY_DEFINITIONS.put("mahj", "mahjong");
        ENTITY_DEFINITIONS.put("math", "mathematics");
        ENTITY_DEFINITIONS.put("mech", "mechanical engineering");
        ENTITY_DEFINITIONS.put("med", "medicine");
        ENTITY_DEFINITIONS.put("met", "climate, weather");
        ENTITY_DEFINITIONS.put("mil", "military");
        ENTITY_DEFINITIONS.put("music", "music");
        ENTITY_DEFINITIONS.put("ornith", "ornithology");
        ENTITY_DEFINITIONS.put("paleo", "paleontology");
        ENTITY_DEFINITIONS.put("pathol", "pathology");
        ENTITY_DEFINITIONS.put("pharm", "pharmacy");
        ENTITY_DEFINITIONS.put("phil", "philosophy");
        ENTITY_DEFINITIONS.put("photo", "photography");
        ENTITY_DEFINITIONS.put("physics", "physics");
        ENTITY_DEFINITIONS.put("physiol", "physiology");
        ENTITY_DEFINITIONS.put("print", "printing");
        ENTITY_DEFINITIONS.put("psych", "psychology, psychiatry");
        ENTITY_DEFINITIONS.put("Shinto", "Shinto");
        ENTITY_DEFINITIONS.put("shogi", "shogi");
        ENTITY_DEFINITIONS.put("sports", "sports");
        ENTITY_DEFINITIONS.put("stat", "statistics");
        ENTITY_DEFINITIONS.put("sumo", "sumo");
        ENTITY_DEFINITIONS.put("telec", "telecommunications");
        ENTITY_DEFINITIONS.put("tradem", "trademark");
        ENTITY_DEFINITIONS.put("vidg", "video game");
        ENTITY_DEFINITIONS.put("zool", "zoology");
        ENTITY_DEFINITIONS.put("ateji", "ateji (phonetic) reading");
        ENTITY_DEFINITIONS.put("ik", "word containing irregular kana usage");
        ENTITY_DEFINITIONS.put("iK", "word containing irregular kanji usage");
        ENTITY_DEFINITIONS.put("io", "irregular okurigana usage");
        ENTITY_DEFINITIONS.put("oK", "word containing out-dated kanji");
        ENTITY_DEFINITIONS.put("abbr", "abbreviation");
        ENTITY_DEFINITIONS.put("arch", "archaism");
        ENTITY_DEFINITIONS.put("char", "character");
        ENTITY_DEFINITIONS.put("chn", "children's language");
        ENTITY_DEFINITIONS.put("col", "colloquialism");
        ENTITY_DEFINITIONS.put("company", "company name");
        ENTITY_DEFINITIONS.put("creat", "creature");
        ENTITY_DEFINITIONS.put("dated", "dated term");
        ENTITY_DEFINITIONS.put("dei", "deity");
        ENTITY_DEFINITIONS.put("derog", "derogatory");
        ENTITY_DEFINITIONS.put("ev", "event");
        ENTITY_DEFINITIONS.put("fam", "familiar language");
        ENTITY_DEFINITIONS.put("fem", "female term or language");
        ENTITY_DEFINITIONS.put("fict", "fiction");
        ENTITY_DEFINITIONS.put("given", "given name or forename, gender not specified");
        ENTITY_DEFINITIONS.put("hist", "historical term");
        ENTITY_DEFINITIONS.put("hon", "honorific or respectful (sonkeigo) language");
        ENTITY_DEFINITIONS.put("hum", "humble (kenjougo) language");
        ENTITY_DEFINITIONS.put("id", "idiomatic expression");
        ENTITY_DEFINITIONS.put("joc", "jocular, humorous term");
        ENTITY_DEFINITIONS.put("leg", "legend");
        ENTITY_DEFINITIONS.put("litf", "literary or formal term");
        ENTITY_DEFINITIONS.put("m-sl", "manga slang");
        ENTITY_DEFINITIONS.put("male", "male term or language");
        ENTITY_DEFINITIONS.put("myth", "mythology");
        ENTITY_DEFINITIONS.put("net-sl", "Internet slang");
        ENTITY_DEFINITIONS.put("obj", "object");
        ENTITY_DEFINITIONS.put("obs", "obsolete term");
        ENTITY_DEFINITIONS.put("obsc", "obscure term");
        ENTITY_DEFINITIONS.put("on-mim", "onomatopoeic or mimetic word");
        ENTITY_DEFINITIONS.put("organization", "organization name");
        ENTITY_DEFINITIONS.put("oth", "other");
        ENTITY_DEFINITIONS.put("person", "full name of a particular person");
        ENTITY_DEFINITIONS.put("place", "place name");
        ENTITY_DEFINITIONS.put("poet", "poetical term");
        ENTITY_DEFINITIONS.put("pol", "polite (teineigo) language");
        ENTITY_DEFINITIONS.put("product", "product name");
        ENTITY_DEFINITIONS.put("proverb", "proverb");
        ENTITY_DEFINITIONS.put("quote", "quotation");
        ENTITY_DEFINITIONS.put("rare", "rare");
        ENTITY_DEFINITIONS.put("relig", "religion");
        ENTITY_DEFINITIONS.put("sens", "sensitive");
        ENTITY_DEFINITIONS.put("serv", "service");
        ENTITY_DEFINITIONS.put("sl", "slang");
        ENTITY_DEFINITIONS.put("station", "railway station");
        ENTITY_DEFINITIONS.put("surname", "family or surname");
        ENTITY_DEFINITIONS.put("uk", "word usually written using kana alone");
        ENTITY_DEFINITIONS.put("unclass", "unclassified name");
        ENTITY_DEFINITIONS.put("vulg", "vulgar expression or word");
        ENTITY_DEFINITIONS.put("work", "work of art, literature, music, etc. name");
        ENTITY_DEFINITIONS.put("X", "rude or X-rated term (not displayed in educational software)");
        ENTITY_DEFINITIONS.put("yoji", "yojijukugo");
        ENTITY_DEFINITIONS.put("adj-f", "noun or verb acting prenominally");
        ENTITY_DEFINITIONS.put("adj-i", "adjective (keiyoushi)");
        ENTITY_DEFINITIONS.put("adj-ix", "adjective (keiyoushi) - yoi/ii class");
        ENTITY_DEFINITIONS.put("adj-kari", "'kari' adjective (archaic)");
        ENTITY_DEFINITIONS.put("adj-ku", "'ku' adjective (archaic)");
        ENTITY_DEFINITIONS.put("adj-na", "adjectival nouns or quasi-adjectives (keiyodoshi)");
        ENTITY_DEFINITIONS.put("adj-nari", "archaic/formal form of na-adjective");
        ENTITY_DEFINITIONS.put("adj-no", "nouns which may take the genitive case particle 'no'");
        ENTITY_DEFINITIONS.put("adj-pn", "pre-noun adjectival (rentaishi)");
        ENTITY_DEFINITIONS.put("adj-shiku", "'shiku' adjective (archaic)");
        ENTITY_DEFINITIONS.put("adj-t", "'taru' adjective");
        ENTITY_DEFINITIONS.put("adv", "adverb (fukushi)");
        ENTITY_DEFINITIONS.put("adv-to", "adverb taking the 'to' particle");
        ENTITY_DEFINITIONS.put("aux", "auxiliary");
        ENTITY_DEFINITIONS.put("aux-adj", "auxiliary adjective");
        ENTITY_DEFINITIONS.put("aux-v", "auxiliary verb");
        ENTITY_DEFINITIONS.put("conj", "conjunction");
        ENTITY_DEFINITIONS.put("cop", "copula");
        ENTITY_DEFINITIONS.put("ctr", "counter");
        ENTITY_DEFINITIONS.put("exp", "expressions (phrases, clauses, etc.)");
        ENTITY_DEFINITIONS.put("int", "interjection (kandoushi)");
        ENTITY_DEFINITIONS.put("n", "noun (common) (futsuumeishi)");
        ENTITY_DEFINITIONS.put("n-adv", "adverbial noun (fukushitekimeishi)");
        ENTITY_DEFINITIONS.put("n-pr", "proper noun");
        ENTITY_DEFINITIONS.put("n-pref", "noun, used as a prefix");
        ENTITY_DEFINITIONS.put("n-suf", "noun, used as a suffix");
        ENTITY_DEFINITIONS.put("n-t", "noun (temporal) (jisoumeishi)");
        ENTITY_DEFINITIONS.put("num", "numeric");
        ENTITY_DEFINITIONS.put("pn", "pronoun");
        ENTITY_DEFINITIONS.put("pref", "prefix");
        ENTITY_DEFINITIONS.put("prt", "particle");
        ENTITY_DEFINITIONS.put("suf", "suffix");
        ENTITY_DEFINITIONS.put("unc", "unclassified");
        ENTITY_DEFINITIONS.put("v-unspec", "verb unspecified");
        ENTITY_DEFINITIONS.put("v1", "Ichidan verb");
        ENTITY_DEFINITIONS.put("v1-s", "Ichidan verb - kureru special class");
        ENTITY_DEFINITIONS.put("v2a-s", "Nidan verb with 'u' ending (archaic)");
        ENTITY_DEFINITIONS.put("v2b-k", "Nidan verb (upper class) with 'bu' ending (archaic)");
        ENTITY_DEFINITIONS.put("v2b-s", "Nidan verb (lower class) with 'bu' ending (archaic)");
        ENTITY_DEFINITIONS.put("v2d-k", "Nidan verb (upper class) with 'dzu' ending (archaic)");
        ENTITY_DEFINITIONS.put("v2d-s", "Nidan verb (lower class) with 'dzu' ending (archaic)");
        ENTITY_DEFINITIONS.put("v2g-k", "Nidan verb (upper class) with 'gu' ending (archaic)");
        ENTITY_DEFINITIONS.put("v2g-s", "Nidan verb (lower class) with 'gu' ending (archaic)");
        ENTITY_DEFINITIONS.put("v2h-k", "Nidan verb (upper class) with 'hu/fu' ending (archaic)");
        ENTITY_DEFINITIONS.put("v2h-s", "Nidan verb (lower class) with 'hu/fu' ending (archaic)");
        ENTITY_DEFINITIONS.put("v2k-k", "Nidan verb (upper class) with 'ku' ending (archaic)");
        ENTITY_DEFINITIONS.put("v2k-s", "Nidan verb (lower class) with 'ku' ending (archaic)");
        ENTITY_DEFINITIONS.put("v2m-k", "Nidan verb (upper class) with 'mu' ending (archaic)");
        ENTITY_DEFINITIONS.put("v2m-s", "Nidan verb (lower class) with 'mu' ending (archaic)");
        ENTITY_DEFINITIONS.put("v2n-s", "Nidan verb (lower class) with 'nu' ending (archaic)");
        ENTITY_DEFINITIONS.put("v2r-k", "Nidan verb (upper class) with 'ru' ending (archaic)");
        ENTITY_DEFINITIONS.put("v2r-s", "Nidan verb (lower class) with 'ru' ending (archaic)");
        ENTITY_DEFINITIONS.put("v2s-s", "Nidan verb (lower class) with 'su' ending (archaic)");
        ENTITY_DEFINITIONS.put("v2t-k", "Nidan verb (upper class) with 'tsu' ending (archaic)");
        ENTITY_DEFINITIONS.put("v2t-s", "Nidan verb (lower class) with 'tsu' ending (archaic)");
        ENTITY_DEFINITIONS.put("v2w-s", "Nidan verb (lower class) with 'u' ending and 'we' conjugation (archaic)");
        ENTITY_DEFINITIONS.put("v2y-k", "Nidan verb (upper class) with 'yu' ending (archaic)");
        ENTITY_DEFINITIONS.put("v2y-s", "Nidan verb (lower class) with 'yu' ending (archaic)");
        ENTITY_DEFINITIONS.put("v2z-s", "Nidan verb (lower class) with 'zu' ending (archaic)");
        ENTITY_DEFINITIONS.put("v4b", "Yodan verb with 'bu' ending (archaic)");
        ENTITY_DEFINITIONS.put("v4g", "Yodan verb with 'gu' ending (archaic)");
        ENTITY_DEFINITIONS.put("v4h", "Yodan verb with 'hu/fu' ending (archaic)");
        ENTITY_DEFINITIONS.put("v4k", "Yodan verb with 'ku' ending (archaic)");
        ENTITY_DEFINITIONS.put("v4m", "Yodan verb with 'mu' ending (archaic)");
        ENTITY_DEFINITIONS.put("v4n", "Yodan verb with 'nu' ending (archaic)");
        ENTITY_DEFINITIONS.put("v4r", "Yodan verb with 'ru' ending (archaic)");
        ENTITY_DEFINITIONS.put("v4s", "Yodan verb with 'su' ending (archaic)");
        ENTITY_DEFINITIONS.put("v4t", "Yodan verb with 'tsu' ending (archaic)");
        ENTITY_DEFINITIONS.put("v5aru", "Godan verb - -aru special class");
        ENTITY_DEFINITIONS.put("v5b", "Godan verb with 'bu' ending");
        ENTITY_DEFINITIONS.put("v5g", "Godan verb with 'gu' ending");
        ENTITY_DEFINITIONS.put("v5k", "Godan verb with 'ku' ending");
        ENTITY_DEFINITIONS.put("v5k-s", "Godan verb - Iku/Yuku special class");
        ENTITY_DEFINITIONS.put("v5m", "Godan verb with 'mu' ending");
        ENTITY_DEFINITIONS.put("v5n", "Godan verb with 'nu' ending");
        ENTITY_DEFINITIONS.put("v5r", "Godan verb with 'ru' ending");
        ENTITY_DEFINITIONS.put("v5r-i", "Godan verb with 'ru' ending (irregular verb)");
        ENTITY_DEFINITIONS.put("v5s", "Godan verb with 'su' ending");
        ENTITY_DEFINITIONS.put("v5t", "Godan verb with 'tsu' ending");
        ENTITY_DEFINITIONS.put("v5u", "Godan verb with 'u' ending");
        ENTITY_DEFINITIONS.put("v5u-s", "Godan verb with 'u' ending (special class)");
        ENTITY_DEFINITIONS.put("v5uru", "Godan verb - Uru old class verb (old form of Eru)");
        ENTITY_DEFINITIONS.put("vi", "intransitive verb");
        ENTITY_DEFINITIONS.put("vk", "Kuru verb - special class");
        ENTITY_DEFINITIONS.put("vn", "irregular nu verb");
        ENTITY_DEFINITIONS.put("vr", "irregular ru verb, plain form ends with -ri");
        ENTITY_DEFINITIONS.put("vs", "noun or participle which takes the aux. verb suru");
        ENTITY_DEFINITIONS.put("vs-c", "su verb - precursor to the modern suru");
        ENTITY_DEFINITIONS.put("vs-i", "suru verb - included");
        ENTITY_DEFINITIONS.put("vs-s", "suru verb - special class");
        ENTITY_DEFINITIONS.put("vt", "transitive verb");
        ENTITY_DEFINITIONS.put("vz", "Ichidan verb - zuru verb (alternative form of -jiru verbs)");
        ENTITY_DEFINITIONS.put("gikun", "gikun (meaning as reading) or jukujikun (special kanji reading)");
        ENTITY_DEFINITIONS.put("ok", "out-dated or obsolete kana usage");
        ENTITY_DEFINITIONS.put("uK", "word usually written using kanji alone");
    }

    @Override
    public Object resolveEntity(String publicID, String systemID, String baseURI, String namespace) throws XMLStreamException {
        return ENTITY_DEFINITIONS.get(namespace);
    }
}
