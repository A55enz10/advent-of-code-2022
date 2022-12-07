fun main() {

    fun part1(input: List<String>): Int {
        return getFilesystem(input).directoryList.filter { it.getSize() <= 100000 }.sumOf { it.getSize() }
    }

    fun part2(input: List<String>): Int {
        val memToFree = getFilesystem(input).fileList.sumOf { it.getSize() } - 40000000
        return getFilesystem(input).directoryList
            .filter { it.getSize() >= (memToFree) }
            .minByOrNull { it.getSize() }
            ?.getSize() ?: -1
    }

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

fun getFilesystem(input: List<String>): MyFileSystem {
    
    val fs = MyFileSystem("/")
    
    var currentDir = fs.root
    input.forEach { line ->
        if (line.startsWith("$")) {// it's a command
            if (line.contains(" cd ")) {
                val nextDirName = line.split(" cd ")[1]
                currentDir = if (nextDirName == "..") {
                    // go up one directory
                    currentDir.parent?:currentDir
                } else {
                    // go down one directory
                    currentDir.subdirectories.firstOrNull { dir -> dir.name == nextDirName } ?: currentDir
                }
            }
        } else { // it's file or directory
            val (objSize, objName) = line.split(" ")
            if (objSize == "dir") {
                currentDir.subdirectories
                    .filter { dir -> dir.name == objName }
                    .ifEmpty { currentDir.addSubdirectory(objName) }
            } else {
                currentDir.files
                    .filter { file -> file.name == objName }
                    .ifEmpty { currentDir.addFile(objName, objSize.toInt()) }
            }
        }
        
    }

    return fs
}

class MyFileSystem(fsName: String) {
    val root = Directory(this, null, fsName, "/")
    val directoryList = mutableListOf<Directory>()
    val fileList = mutableListOf<File>()
}

class Directory(private val fs: MyFileSystem, val parent: Directory?, val name: String, path: String) {
    private val fullPath = if (name == "/") "/" else "$path$name/"
    var subdirectories = mutableListOf<Directory>()
    var files = mutableListOf<File>()
    
    fun addSubdirectory(subName: String) {
        val newDir = Directory(fs, this, subName, this.fullPath)
        subdirectories.add(newDir)
        fs.directoryList.add(newDir)
    }
    
    fun addFile(fileName: String, fileSize: Int) {
        val newFile = File(fileName, fileSize)
        files.add(newFile)
        fs.fileList.add(newFile)
    }
    
    fun getSize(): Int {
        var sum = 0
        for (i in 0 until files.size) sum += files[i].getSize()
        for (i in 0 until subdirectories.size) sum += subdirectories[i].getSize()
        return sum
    }

}

open class File(open val name: String, private val fileSize: Int) {
    open fun getSize(): Int {
        return fileSize
    }
}
