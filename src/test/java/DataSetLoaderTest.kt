import dev.isteam.chatbot.dl.api.dataset.loader.MINDsLabDataSetLoader
import org.jetbrains.annotations.TestOnly

class DataSetLoaderTest {

    fun test() : MINDsLabDataSetLoader{
        var path = "C:\\Users\\Singlerr\\Desktop\\ISTEAM\\AI 데이터 셋\\일반상식\\02_squad_질문_답변_제시문_말뭉치\\ko_wiki_v1_squad.json"
        var loader = MINDsLabDataSetLoader(path = path)
        return loader
    }
}