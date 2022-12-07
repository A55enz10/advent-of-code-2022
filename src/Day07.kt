fun main() {

    fun part1(input: List<String>): Int {
        return getFilesystem(input).directoryList.filter { dir -> dir.getSize() <= 100000 }.sumOf { dir -> dir.getSize() }
    }

    fun part2(input: List<String>): Int {
        return 0
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
                if (nextDirName.equals("..")) {
                    // go up one directory
                    currentDir = currentDir.parent?:currentDir
                } else {
                    // go down one directory
                    currentDir = currentDir.subdirectories
                        .filter { dir -> dir.name.equals(nextDirName) }
                        .firstOrNull()?: currentDir
                }
            }
        } else { // it's file or directory
            val (objSize, objName) = line.split(" ")
            if (objSize.equals("dir")) {
                currentDir.subdirectories
                    .filter { dir -> dir.name.equals(objName) }
                    .ifEmpty { currentDir.addSubdirectory(objName) }
            } else {
                currentDir.files
                    .filter { file -> file.name.equals(objName) }
                    .ifEmpty { currentDir.addFile(objName, objSize.toInt()) }
            }
        }
        
    }

    return fs
}

class MyFileSystem(val fsName: String) {
    val root = Directory(this, null, fsName, "/")
    val directoryList = mutableListOf<Directory>()
    val fileList = mutableListOf<File>()
}

class Directory(val fs: MyFileSystem, val parent: Directory?, val name: String, path: String) {
    val fullPath = if (name .equals("/") ) "/" else path + name + "/"
    var subdirectories = mutableListOf<Directory>()
    var files = mutableListOf<File>()
    
    fun addSubdirectory(subName: String) {
        val newDir = Directory(fs, this, subName, this.fullPath)
        subdirectories.add(newDir)
        fs.directoryList.add(newDir)
    }
    
    fun addFile(fileName: String, fileSize: Int) {
        val newFile = File(fileName, fileSize, this.fullPath)
        files.add(newFile)
        fs.fileList.add(newFile)
    }
    
    fun isDirectory(): Boolean { return true}
    fun getSize(): Int {
        var sum = 0
        for (i in 0 until files.size) sum += files[i].getSize()
        for (i in 0 until subdirectories.size) sum += subdirectories[i].getSize()
        return sum
    }
    
    fun containsDirectory(dirName: String): Boolean {
        return subdirectories.filter { it.name.equals(dirName)}.size > 0
    }
}

open class File(open val name: String, val fileSize: Int, val path: String ) {
    val fullPath = path + "/"
    
    open fun getSize(): Int {
        return fileSize
    }
}
